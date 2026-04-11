package frc.robot.subsystems.Intake;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import pabeles.concurrency.ConcurrencyOps.Reset;
import edu.wpi.first.wpilibj.motorcontrol.Spark;


public class Shooter extends SubsystemBase {
public TalonSRX shooter= new TalonSRX(0);
public TalonSRX hopper= new TalonSRX(1);
public Spark Hop = new Spark(0);
TalonSRXConfiguration config = new TalonSRXConfiguration();
 boolean Shooting =true;
 boolean Feeding =true;
Timer timer = new Timer();
//2
public Shooter() {
    config.peakCurrentLimit = 40;
    config.peakCurrentDuration = 1500;
    config.continuousCurrentLimit = 30;
    shooter.configAllSettings(config);
    hopper.configAllSettings(config);
}

public Command intake() {
    return runOnce( () -> {
        shooter.set(TalonSRXControlMode.PercentOutput, -1);
        boolean Shooting = true;
       SmartDashboard.putBoolean("intake", Shooting);

    });}
    public Command intake_2() {
    return runOnce( () -> {
        shooter.set(TalonSRXControlMode.PercentOutput, -.75);
        boolean Shooting = true;
       SmartDashboard.putBoolean("intake", Shooting);
    
    });}

    public Command intakeRev() {
    return runOnce( () -> {
        shooter.set(TalonSRXControlMode.PercentOutput, 1);
         boolean Shooting = true;
       SmartDashboard.putBoolean("intake", Shooting);
    
    });}

public Command intakeStop() {
    return runOnce( () -> {
        shooter.set(TalonSRXControlMode.PercentOutput, 0);
            boolean Shooting = false;
       SmartDashboard.putBoolean("intake", Shooting);
    
    });}

public Command hopperIn() {
    timer.start();
    return runOnce( () -> {
    hopper.set(TalonSRXControlMode.PercentOutput, 1);
      boolean Feeding = true;
       SmartDashboard.putBoolean("feeding", Feeding);
       SmartDashboard.putNumber("hopper timer", timer.get());
    });}

public Command hopperOut() {
    return runOnce( () -> {
        hopper.set(TalonSRXControlMode.PercentOutput, -1);
      boolean Feeding = true;
       SmartDashboard.putBoolean("feeding", Feeding);
    });}

public Command hopperStop() {
    return runOnce( () -> {
        hopper.set(TalonSRXControlMode.PercentOutput, 0);
    boolean Feeding = false;
       SmartDashboard.putBoolean("feeding", Feeding);
       
    });}


public Command HopStop() {
    return runOnce( () -> {
        Hop.set(0);
       
    });} 

public Command HopIn() {
return runOnce( () -> {
    Hop.set(.5);
    
});} 

public Command HopOut() {
return runOnce( () -> {
    Hop.set(-.5);
    
});} 
















































































}
