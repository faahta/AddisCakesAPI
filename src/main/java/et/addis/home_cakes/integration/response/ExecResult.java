package et.addis.home_cakes.integration.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Fassil on 28/01/22.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecResult implements Serializable {
    static final long serialVersionUID = 1;
    private Boolean esito = null;
    private String msg = null;
    private String[] params = null;
}
