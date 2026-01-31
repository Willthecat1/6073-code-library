package frc.robot.subsystems.Intake;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Shooter extends SubsystemBase {
public TalonSRX shooter= new TalonSRX(0);
public TalonSRX hopper= new TalonSRX(1);
TalonSRXConfiguration config = new TalonSRXConfiguration();

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
    
    });}

public Command intakeStop() {
    return runOnce( () -> {
        shooter.set(TalonSRXControlMode.PercentOutput, 0);
    
    });}

public Command hopperIn() {
    return runOnce( () -> {
        hopper.set(TalonSRXControlMode.PercentOutput, 1);
    
    });}

public Command hopperOut() {
    return runOnce( () -> {
        hopper.set(TalonSRXControlMode.PercentOutput, -1);
    
    });}

public Command hopperStop() {
    return runOnce( () -> {
        hopper.set(TalonSRXControlMode.PercentOutput, 0);
    
    });}











































































}
