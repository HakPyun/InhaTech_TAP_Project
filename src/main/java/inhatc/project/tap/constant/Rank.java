package inhatc.project.tap.constant;

import lombok.Getter;

@Getter
public enum Rank {
    USER("ROLE_USER"),EDITOR("ROLE_EDITOR"),ADMIN("ROLE_ADMIN");

    Rank(String value){
        this.value=value;
    }
    private String value;
}
