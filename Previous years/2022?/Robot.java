/*
/||||||||| /|||||||\  ||||||||||| |||||||\
|||        ||     ||          //        ||
|||||||||\ ||     ||         //    |||||||
|||    ||| ||     ||        //          ||
\||||||||/ \|||||||/       //      ||||||/                                                                              The Coding Team
*/
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.cameraserver.CameraServer; 


public class Robot extends TimedRobot {
  private Spark M_0 = new Spark(0);
  private Spark M_1 = new Spark(1);
  private Spark ShooterL = new Spark(2);
  private Spark Aim = new Spark(3);
  private Joystick J_0 = new Joystick(0);
  Compressor C_0 = new Compressor(0, PneumaticsModuleType.CTREPCM);
  private DifferentialDrive Drive = new DifferentialDrive(M_0, M_1);
  private Timer time = new Timer();
  private Solenoid IN = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
  int S_1;
  private Spark B_I = new Spark(4);
  private TalonFX Climber_L = new TalonFX(6);
  private TalonFX Climber_R = new TalonFX(5);
  private Spark Climber_0 = new Spark(7);
  private Spark I_U = new Spark(8);
  private Spark ShooterR = new Spark(9);
  private SendableChooser Auto = new SendableChooser<>();   
  private static final String None = "None";
  private static final String Base = "Base";
  private static final String Test = "Test";
  private static final String Pos = "Pos";
  private double x ;
  private DoubleSolenoid L_U = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3);
  public boolean DPadUp() {
    if ((J_0.getPOV(0) >= 315 || J_0.getPOV(0) <= 45) && J_0.getPOV(0) != -1)
      return true;
    else
      return false;
  }

  public boolean DPadDown() {
    if (J_0.getPOV(0) >= 135 && J_0.getPOV(0) <= 225)
      return true;
    else
      return false;
  }

  public boolean DPadLeft() {
    if (J_0.getPOV(0) >= 225 && J_0.getPOV(0) <= 315)
      return true;
    else
      return false;
  }

  public boolean DPadRight() {
    if (J_0.getPOV(0) >= 45 && J_0.getPOV(0) <= 135)
      return true;
    else
      return false;
  }
  @Override
public void robotInit() {
  CameraServer.startAutomaticCapture(0);
  CameraServer.startAutomaticCapture(1);

  Climber_L.configFactoryDefault();
  Climber_L.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 35, 1.0));
  Climber_L.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 35, 0.5));
  Climber_R.configFactoryDefault();
  Climber_R.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40,35, 1.0));
  Climber_R.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 35, 0.5));

C_0.enableDigital();
C_0.start();
S_1 = 0;
 Auto.setDefaultOption("None", None);
 Auto.addOption("Base", Base);
 Auto.addOption("Test", Test);
 SmartDashboard.putData("Auto choices", Auto);


}
  @Override
public void robotPeriodic() {}

  
  @Override
public void autonomousInit() {
  time.reset();
  time.start();
}
  
  @Override
public void autonomousPeriodic() {
if(Auto.getSelected() == None){

}else if(Auto.getSelected() == Base){
    if(time.get()<1)
    {M_0.set(-0.5);
    M_1.set(0.5);}
    else{
    M_0.set(0);
    M_1.set(0);
    time.stop();}
}else if(Auto.getSelected() == Test){
  if(time.get()<1)
  {M_0.set(-0.5);
    M_1.set(0.5);}
  else{
    M_0.set(0);
    M_1.set(0);
}
if(time.get() > 2){
  IN.set(true); 
}else{IN.set(false); }
if(time.get() > 2.25){
  IN.set(false);
}
if(time.get()>1.0)
{ShooterL.set(-1);
  ShooterR.set(1);}

else if(time.get() > 2.25 ){
  ShooterL.set(0);
  ShooterR.set(0);}
if(time.get()>2.75)
  {time.stop();}
}else {time.stop();}
}
 
  
  @Override
public void teleopInit() {

}
  

  @Override
public void teleopPeriodic() {
  Drive.arcadeDrive(-J_0.getX(),- J_0.getY());
  if (J_0.getRawAxis(3) == 1) {
    ShooterL.set(-1);
   ShooterR.set(1);}
  else{
      ShooterL.set(0);
      ShooterR.set(0);
} 
if (J_0.getRawButton(10)) {
  IN.set(true);}
else{
  IN.set(false); } 

  if (J_0.getRawButton(4)) {
    Aim.set(.8);
  } else if (J_0.getRawButton(2)) {
    Aim.set(-.8);
  } else {Aim.set(0); }


if (J_0.getRawButton(5)) {
    B_I.set(.25);}
    else if(J_0.getRawAxis(2) == 1){
      B_I.set(-0.25);
    }else{
    B_I.set(0);}
    if (J_0.getRawButton(3)) {
      Climber_L.set(ControlMode.PercentOutput, -1);
      Climber_R.set(ControlMode.PercentOutput, 1);
    
    } else if (J_0.getRawButton(1)) { 
      Climber_L.set(ControlMode.PercentOutput, 1);
      Climber_R.set(ControlMode.PercentOutput, -1);
  } else{
    Climber_L.set(ControlMode.PercentOutput, 0);
    Climber_R.set(ControlMode.PercentOutput, 0);}

    if (DPadUp() == true) {
      Climber_0.set(1);
      L_U.set(Value.kForward);} 
      
    else if (DPadDown() == true) {
    Climber_0.set(-1);
    L_U.set(Value.kReverse);
  } else {L_U.set(Value.kOff);
   Climber_0.set(0);
  }

    if(DPadLeft() == true){
   I_U.set(1);
}else if(DPadRight() == true) {
  I_U.set(-1);}
 else{I_U.set(0);}
x = Climber_L.getSelectedSensorPosition();
SmartDashboard.putNumber("Pos" ,x);}



  
  @Override
public void disabledInit() {}

  
  @Override
public void disabledPeriodic() {}

  
  @Override
public void testInit() {}

  
  @Override
public void testPeriodic() {}

}
