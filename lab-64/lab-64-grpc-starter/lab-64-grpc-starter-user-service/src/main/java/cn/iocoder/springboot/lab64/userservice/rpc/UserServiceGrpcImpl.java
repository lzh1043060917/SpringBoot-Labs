package cn.iocoder.springboot.lab64.userservice.rpc;

import cn.iocoder.springboot.lab64.userservice.Model;
import cn.iocoder.springboot.lab64.userservice.api.*;
import cn.iocoder.springboot.lab64.userservice.utils.SerializeUtils;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService // 注解，用于被扫描  自动创建并运行一个 gRPC 服务，内嵌在 spring-boot 应用中
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {
    // 区别是去掉了config配置类，使用yml配置端口
    @Override // 实现service-api
    public void get(UserGetRequest request, StreamObserver<UserBizGetResponse> responseObserver) {
        // 创建响应对象
        Model model = new Model();
        model.setId(request.getId());
        model.setName("没有昵称：" + request.getId());
        model.setGender(request.getId() % 2 + 1);
        UserBizGetResponse.Builder builder = UserBizGetResponse.newBuilder();
        builder.setValue(SerializeUtils.serialize(model));
        // 返回响应
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void create(UserCreateRequest request, StreamObserver<UserCreateResponse> responseObserver) {
        // 创建响应对象
        UserCreateResponse.Builder builder = UserCreateResponse.newBuilder();
        builder.setId((int) (System.currentTimeMillis() / 1000));
        // 返回响应
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

}
