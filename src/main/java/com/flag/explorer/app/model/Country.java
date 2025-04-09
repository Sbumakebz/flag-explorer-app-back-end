package com.flag.explorer.app.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Country implements Serializable {
    private static final long serialVersionUID = 6911011610110810014L;
	
    @NotBlank
    private String name;

    @NotBlank
    private String flag;
}
