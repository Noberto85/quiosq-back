package com.webone.quiosq.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MesaQrcodeResponse {

    private byte[] pdf;
}
