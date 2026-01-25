package com.webone.quiosq.service;

import com.webone.quiosq.controller.response.DashboardResponse;
import java.util.UUID;

public interface DashboardService {
    DashboardResponse load(UUID quiosqueId);
}
