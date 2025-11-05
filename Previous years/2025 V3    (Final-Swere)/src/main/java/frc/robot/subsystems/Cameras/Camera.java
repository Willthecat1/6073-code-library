// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Cameras;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.OutputStream;
import java.io.PrintStream;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




public class Camera extends SubsystemBase {
UsbCamera camera0 = CameraServer.startAutomaticCapture(0);
  

  
 
  public Camera() {
   
    


  }



public Command Cam1() {
  return runOnce(
      () -> {
        CameraServer.getServer().setSource(camera0);
        SmartDashboard.putString("Active Camera", "Camera 1");


      });
}















  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
