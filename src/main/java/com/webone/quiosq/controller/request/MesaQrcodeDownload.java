package com.webone.quiosq.controller.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record MesaQrcodeDownload(
    @NotNull List<Long> ids
) {

}
