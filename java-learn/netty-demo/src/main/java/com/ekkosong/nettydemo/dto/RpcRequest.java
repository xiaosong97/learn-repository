package com.ekkosong.nettydemo.dto;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RpcRequest {
    private String interfaceName;
    private String methodName;
}
