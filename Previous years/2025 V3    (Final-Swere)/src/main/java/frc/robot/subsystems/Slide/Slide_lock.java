// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Slide;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Slide_lock extends SubsystemBase {
DoubleSolenoid Mlock_sole = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 3,4);
  public Slide_lock() {
    Mlock_sole .set(Value.kForward); 
}
public Command unlock() {
  // Inline construction of command goes here.
  // Subsystem::RunOnce implicitly requires `this` subsystem.
  return runOnce(
      () -> {
        Mlock_sole .set(Value.kReverse); /* one-time action goes here */
      });
}
public Command lock() {
  // Inline construction of command goes here.
  // Subsystem::RunOnce implicitly requires `this` subsystem.
  return runOnce(
      () -> {
        Mlock_sole .set(Value.kForward); /* one-time action goes here*/
});
}



 
 
 
 
 
 
 
 
  @Override
  public void periodic() {
    
  }






}
