// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Slide;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Slide_Door extends SubsystemBase {
DoubleSolenoid door_sole1 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 5,6);
  boolean door_open =true;

public Slide_Door() {
   

}
public Command Open_door() {
  return runOnce(
      () -> {
       door_sole1.set(Value.kReverse);
       boolean door_open = true;
       SmartDashboard.putBoolean("Door Open", door_open);});
}
public Command Close_door() {
  return runOnce(
      () -> {
       door_sole1.set(Value.kForward);
       boolean door_open = false;
       SmartDashboard.putBoolean("Door Open", door_open);
});
}



 
 
 
 
 
 
 
 
  @Override
  public void periodic() {
    
  }






}
