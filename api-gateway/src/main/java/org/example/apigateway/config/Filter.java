//package org.example.apigateway.config;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.example.commonservice.utils.JwtUtil;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Filter extends AbstractGatewayFilterFactory<Filter.Config>{
//    public Filter() {
//        super(Config.class);
//    }
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class Config{
//        private String message;
//    }
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            System.out.println("Message: " + config.getMessage());
//            return chain.filter(exchange);
//        };
//    }
//}
