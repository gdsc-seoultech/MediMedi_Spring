package seoultech.gdsc.mediMedi.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medi {
    @Id
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String name;

    private String entp; //기업명

    private String effect; //효능

    private String usingMethod; // 사용법

    private String caution; // 경고

    private String notice; // 주의사항

    private String interact; // 상호작용

    private String sideEffect; // 부작용

    private String storageMethod; // 보관방법


}
