package com.ekkosong.nettydemo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class RpcResponse {
    private String message;
}
