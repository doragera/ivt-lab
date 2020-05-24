package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primary;
  private TorpedoStore secondary;

  @BeforeEach
  public void init(){
    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class); 
    this.ship = new GT4500(primary, secondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }


  @Test
  public void fireTorpedo_Single_From_Secondary(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Both_Empty(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    //when(secondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    assertEquals(false, result);
  }
  

  @Test
  public void fireTorpedo_Single_Primary_Already_Fired(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    when(secondary.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Primary_Already_Fired_Secondary_Empty(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Primary_Already_Fired_Both_Empty(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(false, result);
  }
  
  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Failure(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_FireSecondary(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_FireSecondaryFailure(){
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    when(primary.fire(1)).thenReturn(false);
    when(secondary.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Undefined_Mode(){
    // Arrange
    FiringMode nullType = null;
    // Act

    // Assert
    assertThrows(NullPointerException.class, ()->ship.fireTorpedo(nullType));
  }
}
