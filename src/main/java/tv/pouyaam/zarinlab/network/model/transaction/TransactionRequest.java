package tv.pouyaam.zarinlab.network.model.transaction;

import tv.pouyaam.zarinlab.network.model.base.BaseRequestModel;

public class TransactionRequest extends BaseRequestModel{
    private TransactionRequest() {
    }

    public static class Builder {
        public Builder() {
        }

        public TransactionRequest build() {
            return new TransactionRequest();
        }
    }
}
