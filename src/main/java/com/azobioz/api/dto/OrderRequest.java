package com.azobioz.api.dto;

import java.util.List;

public record OrderRequest (List<NewOrderProductRequest> products) {
}
