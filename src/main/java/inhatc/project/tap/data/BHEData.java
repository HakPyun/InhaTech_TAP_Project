package inhatc.project.tap.data;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BHEData implements Serializable {
//    @ManyToOne
//    @JoinColumn(name="shop_id")
    private long shop_id;
    private String sDay;
}
