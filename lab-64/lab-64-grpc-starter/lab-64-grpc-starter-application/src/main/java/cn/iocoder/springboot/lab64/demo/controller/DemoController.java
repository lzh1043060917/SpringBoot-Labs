package cn.iocoder.springboot.lab64.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.iocoder.springboot.lab64.userservice.Model;
import cn.iocoder.springboot.lab64.userservice.api.UserBizGetResponse;
import cn.iocoder.springboot.lab64.userservice.api.UserCreateRequest;
import cn.iocoder.springboot.lab64.userservice.api.UserCreateResponse;
import cn.iocoder.springboot.lab64.userservice.api.UserGetRequest;
import cn.iocoder.springboot.lab64.userservice.api.UserServiceGrpc;
import cn.iocoder.springboot.lab64.userservice.utils.SerializeUtils;
import net.devh.boot.grpc.client.inject.GrpcClient;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GrpcClient("userService")
    private UserServiceGrpc.UserServiceBlockingStub userServiceGrpc;

    @GetMapping("/get")
    public Object get(@RequestParam("id") Integer id) {
        // 创建请求
        UserGetRequest request = UserGetRequest.newBuilder().setId(id).build();
        // 执行 gRPC 请求
        UserBizGetResponse response = userServiceGrpc.get(request);

        // UserGetResponse response = userServiceGrpc.get(request);
        // 响应
        Model model = SerializeUtils.unserialize(response.getValue());
        return model;
    }

    @GetMapping("/create") // 为了方便测试，实际使用 @PostMapping
    public Integer create(@RequestParam("name") String name,
                          @RequestParam("gender") Integer gender) {
        // 创建请求
        UserCreateRequest request = UserCreateRequest.newBuilder()
                .setName(name).setGender(gender).build();
        // 执行 gRPC 请求
        UserCreateResponse response = userServiceGrpc.create(request);
        // 响应
        return response.getId();
    }

}
