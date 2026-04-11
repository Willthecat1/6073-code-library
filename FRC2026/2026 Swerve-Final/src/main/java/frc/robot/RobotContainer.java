// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake.Shooter;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.auto.AutoBuilderException;
import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Limelight.LimeLight;
import edu.wpi.first.cscore.UsbCamera;
//import frc.robot.subsystems.Cameras.Camera;



public class RobotContainer {
    private double MaxSpeed = .4 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.20).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity
   
    
  
    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    
    private final Telemetry logger = new Telemetry(MaxSpeed);
UsbCamera camera0 = CameraServer.startAutomaticCapture(0);
    private final CommandXboxController joystick = new CommandXboxController(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
 private final Shooter Shooter = new Shooter();
 private final LimeLight limeLight = new LimeLight();
    
    
 private final SendableChooser<Command> autoChooser;
 
 
 
 public RobotContainer() {        
        CameraServer.getServer().setSource(camera0);
        SmartDashboard.putString("Active Camera", "Camera 1");
        NamedCommands.registerCommand("Intake", Shooter.intake());
        NamedCommands.registerCommand("IntakeStop", Shooter.intakeStop());
        NamedCommands.registerCommand("HopperIn", Shooter.hopperIn());
        NamedCommands.registerCommand("HopperOut", Shooter.hopperOut());
        NamedCommands.registerCommand("HopperStop", Shooter.hopperStop());
        autoChooser = AutoBuilder.buildAutoChooser("None");
        SmartDashboard.putData("Auto Chooser",autoChooser);
        
        configureBindings();
    }

    private void configureBindings() {
      
      
        joystick.leftBumper().whileTrue(Shooter.intakeRev());
        joystick.leftTrigger().whileTrue(Shooter.intake_2().andThen(Shooter.hopperOut()));
        joystick.leftTrigger().or(joystick.leftBumper()).whileFalse(Shooter.intakeStop().andThen(Shooter.hopperStop()));
        joystick.rightTrigger().whileTrue(Shooter.intake().andThen(Commands.sequence(Commands.waitSeconds(0.5), (Shooter.hopperIn()))));
       joystick.rightTrigger().whileFalse(Shooter.intakeStop().andThen(Shooter.hopperStop()));
       joystick.a().whileTrue(Shooter.HopIn());
       joystick.b().whileTrue(Shooter.HopOut());
      

        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));

       
        


        // Limelight Align
        joystick.x().onTrue(limeLight.alignCommand(drivetrain));


        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
       joystick.povDown().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
       /* Run the path selected from the auto chooser */
       return autoChooser.getSelected();
    }
}
