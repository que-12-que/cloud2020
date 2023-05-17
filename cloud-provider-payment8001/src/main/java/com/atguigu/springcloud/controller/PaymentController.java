package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果：" + result);

        if (result > 0) {
            return new CommonResult(200,"插入成功,serverPort:"+serverPort,result);
        }else {
            return new CommonResult(404,"插入失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")/**/
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("插入结果：" + payment);

        if (payment != null) {
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);
        }else {
            return new CommonResult(404,"查询失败,查询ID为：" + id,null);
        }
    }
    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for(String element : services) {
            log.info("****element:" + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+
                     instance.getHost()+"\t"+
                     instance.getPort()+"\t"+
                     instance.getUri()+"\t"+
                     instance.getInstanceId());
        }
        return this.discoveryClient;
    }
}
