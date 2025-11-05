
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Joystick;

import javax.swing.text.StyleContext.SmallAttributeSet;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;


public class Robot extends TimedRobot {
  private Joystick joy1 = new Joystick(0);
  static final CANSparkMax FL_M = new CANSparkMax(1, MotorType.kBrushless);
  static final CANSparkMax FR_M = new CANSparkMax(2, MotorType.kBrushless);
  static final CANSparkMax RR_M = new CANSparkMax(3, MotorType.kBrushless);
  static final CANSparkMax RL_M = new CANSparkMax(4, MotorType.kBrushless);
  static final CANSparkMax L_1 = new CANSparkMax(5, MotorType.kBrushless);
  static final CANSparkMax L_2 = new CANSparkMax(6, MotorType.kBrushless);
  private RelativeEncoder FL_E;
  private RelativeEncoder FR_E;
  private RelativeEncoder RR_E;
  private RelativeEncoder RL_E;
  double speed;
  double turn;
  double strafe;

   


public boolean DPadUp() {
  if ((joy1.getPOV(0) >= 315 || joy1.getPOV(0) <= 45) && joy1.getPOV(0) != -1)
    return true;
  else
    return false;
   }

public boolean DPadDown() {
  if (joy1.getPOV(0) >= 135 && joy1.getPOV(0) <= 225)
    return true;
  else
    return false;
}

public boolean DPadLeft() {
  if (joy1.getPOV(0) >= 225 && joy1.getPOV(0) <= 315)
    return true;
  else
    return false;
}

public boolean DPadRight() {
  if (joy1.getPOV(0) >= 45 && joy1.getPOV(0) <= 135)
    return true;
  else
    return false;
}

  @Override
  public void robotInit() {
   CameraServer.startAutomaticCapture(0);  
  
  
  }

 
  @Override
  public void robotPeriodic() {
     speed = joy1.getRawAxis(5);
     turn = joy1.getRawAxis(0);
    strafe =joy1.getRawAxis(4);
   
     FL_E = FL_M.getEncoder();
     FR_E = FR_M.getEncoder();
    RR_E = RR_M.getEncoder();
    RL_E = RL_M.getEncoder();

    SmartDashboard.putNumber("FL_E postion", FL_E.getPosition());
    SmartDashboard.putNumber("FL_E velocity", FL_E.getVelocity());
    SmartDashboard.putNumber("FR_E postion", FR_E.getPosition());
    SmartDashboard.putNumber("FR_E velocity", FR_E.getVelocity());
    SmartDashboard.putNumber("RR_E postion", RR_E.getPosition());
    SmartDashboard.putNumber("RR_E velocity", RR_E.getVelocity());
    SmartDashboard.putNumber("RL_E postion", RL_E.getPosition());
    SmartDashboard.putNumber("RL_E velocity", RL_E.getVelocity());
 
 
  }

  
  @Override
  public void autonomousInit() {
    
  }

  
  @Override
  public void autonomousPeriodic() {
   
  }

  
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    FL_M.set(speed+turn+strafe);
    FR_M.set(speed-turn-strafe);
    RL_M.set(speed+turn-strafe);
    RR_M.set(speed-turn+strafe);

    if(joy1.getRawAxis(3) == 1){
     L_1.set(1); 
     L_2.set(1); 
    }else if(joy1.getRawButton(5)){
    L_1.set(-1); 
     L_2.set(-1); 
    }else{
     L_1.set(0); 
     L_2.set(0);  
    }



  }



  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
