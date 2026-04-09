package ru.shlapabank.api.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryAccountsRequest {
    @JsonProperty("account_ids")
    private List<Long> accountIds;
}
