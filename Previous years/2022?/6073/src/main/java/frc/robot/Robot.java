// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;





public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Spark M_0 = new Spark(0);
  private Spark M_1 = new Spark(1);
  private Joystick J_0 = new Joystick(0);
  private DifferentialDrive Drive = new DifferentialDrive(M_1, M_0);
NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  
NetworkTableEntry tx = table.getEntry("tx");
NetworkTableEntry ty = table.getEntry("ty");
NetworkTableEntry ta = table.getEntry("ta");
 

//read values periodically
double x = tx.getDouble(0.0);
double y = ty.getDouble(0.0);
double area = ta.getDouble(0.0);

//post to smart dashboard periodically
SmartDashboard.putNumber("LimelightX", x);
SmartDashboard.putNumber("LimelightY", y);
SmartDashboard.putNumber("LimelightArea", area);
  }

  
  @Override
  public void robotPeriodic() {}

  
  @Override
  public void autonomousInit() {
   
  }

  
  @Override
  public void autonomousPeriodic() {
   
  }

  
  @Override
  public void teleopInit() {
    
  }
  

  
  @Override
  public void teleopPeriodic() {
Drive.arcadeDrive(J_0.getY(), J_0.getX());


}

  
  @Override
  public void disabledInit() {

  }

  
  @Override
  public void disabledPeriodic() {}

  
  @Override
  public void testInit() {}

  
  @Override
  public void testPeriodic() {}
}
