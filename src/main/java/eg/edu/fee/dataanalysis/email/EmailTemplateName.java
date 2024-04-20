package eg.edu.fee.dataanalysis.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ACTIVATION_ACCOUNT("activation_account"),
    STOCK_VOTING_NOTIFY("stock_vote_notify");
    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
