package com.flag.explorer.app.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryDetails implements Serializable {
    private static final long serialVersionUID = 6911011610110810014L;
	
    @NotBlank
    private String name;
	
	@NonNull
    private Integer population;
	
	@NotBlank
    private String capital;

    @NotBlank
    private String flag;
}
