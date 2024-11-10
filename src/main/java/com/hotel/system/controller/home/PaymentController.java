package com.hotel.system.controller.home;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hotel.system.config.AlipayConfig;
import com.hotel.system.entity.Category;
import com.hotel.system.entity.Order;
import com.hotel.system.enums.OrderStatusEnum;
import com.hotel.system.exception.MyBusinessException;
import com.hotel.system.service.CategoryService;
import com.hotel.system.service.OrderService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 言曌
 * @date 2022/5/8 12:29 下午
 */
@Controller
public class PaymentController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PaymentController.class);

    @Value("${openAlipay}")
    private Boolean openAlipay;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/payment/paySuccess")
    public String paySuccess(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "home/paySuccess";
    }


    /**
     * 支付创建
     * PC二维码支付
     *
     * @param orderId
     * @param response
     */
    @RequestMapping("/payment/alipay/create")
    @Transactional(rollbackFor = Exception.class)
    public void alipayCreate(Long orderId,
                             HttpServletResponse response) throws IOException {

        // 如果没有开启支付宝支付，则直接订单创建成功
        if (!openAlipay) {
            response.sendRedirect("/order/" + orderId);
        }

        if (orderId == null) {
            throw new MyBusinessException("订单号不能为空");
        }
        Order order = orderService.get(orderId);
        if (order == null) {
            throw new MyBusinessException("订单不存在");
        }

        // 其他情况，暂不考虑

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);


        String orderCode = order.getCode();
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderCode;
        // 订单名称，必填
        String subject = "订单" + order.getCode();
        // 付款金额，必填
        String total_amount = order.getTotalPrice().toString();
        // 商品描述，可空
        String body = "";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


        // form表单生产
        try {
            // 调用SDK生成表单
            String form = alipayClient.pageExecute(alipayRequest).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException | IOException e) {
            log.error("alipayCreate：", e);
        }
    }

    /**
     * 支付创建
     *
     * @param response
     */
    @PostMapping("/payment/alipay/notify")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public void alipayNotify(HttpServletRequest request,
                             HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException {

        log.info("alipayNotify进来了");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        log.info("alipayNotify params:" + JSON.toJSONString(params));


        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)

        log.info("alipayNotify out_trade_no:" + out_trade_no);
        log.info("alipayNotify trade_no:" + trade_no);
        log.info("alipayNotify trade_status:" + trade_status);
        if ("TRADE_FINISHED".equals(trade_status) || "TRADE_SUCCESS".equals(trade_status)) {
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
            log.info("alipayNotify verify_result:" + verify_result);
            if (verify_result) {
                // 支付成功，更新订单状态为已支付
                Order order = orderService.findByCode(out_trade_no);
                if (order != null) {
                    order.setStatus(OrderStatusEnum.HAS_PAY.getCode());
                    orderService.update(order);
                }
            }

        }
    }

}
