package entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class MixIns {
  
  @JsonIgnoreProperties("_links")
  public interface Personas{
    
  }
}
