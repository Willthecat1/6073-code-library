// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import java.util.Timer;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardComponent;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Slide.Slide_Door;
import frc.robot.subsystems.Slide.Slide_Ladder;
import frc.robot.subsystems.Slide.Slide_lock;
import frc.robot.subsystems.Cameras.Camera;






public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

     /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    private final Telemetry logger = new Telemetry(MaxSpeed);
    
    private final CommandXboxController joystick = new CommandXboxController(0);
    Compressor C_0 = new Compressor(0, PneumaticsModuleType.CTREPCM);
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();




/*allows sub systems to be called later in code */
    private final Slide_Door Slide_Door = new Slide_Door();
    private final Slide_Ladder Slide_Ladder = new Slide_Ladder();  
    private final Slide_lock Slide_lock = new Slide_lock();   
    private final Camera Camera = new Camera();
    private Timer time =new Timer();


  

    /* Path follower */
    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
       
        
    /*auton commands ------------------------------------- Too be called in paht planner */
       
                NamedCommands.registerCommand(/*named called in pathplanner*/"door",/*
                    Code called when ran in path pallner*/
                    Slide_Door.Close_door()
                );
                NamedCommands.registerCommand("L_up",Slide_lock.unlock().andThen(Slide_Ladder.Up()));
                NamedCommands.registerCommand("L_down",Slide_lock.unlock().andThen(Slide_Ladder.Down()));
                NamedCommands.registerCommand("L_stop",Slide_Ladder.Stop().andThen(Slide_lock.lock()));
                NamedCommands.registerCommand("door_1",Slide_Door.Open_door());
               
     //*************************************************************
               
               
               autoChooser = AutoBuilder.buildAutoChooser("Tests");
                SmartDashboard.putData("Auto Chooser", autoChooser);
               C_0.enableDigital();
               configureBindings();
                 /** Creates a new Camera. */

           
                
            }
       
           
       
       
       
           private void configureBindings() {
    /*BUTTON MAPS*********************************/
            joystick.x().whileTrue(Slide_Door.Open_door()); 
            joystick.y().whileTrue(Slide_Door.Close_door()); 
            joystick.leftBumper().whileTrue(Slide_lock.unlock().andThen(Slide_Ladder.Up()));
            joystick.rightBumper().whileTrue(Slide_lock.unlock().andThen(Slide_Ladder.Down()));
            joystick.rightBumper().or(joystick.leftBumper()).whileFalse(Slide_Ladder.Stop().andThen(Slide_lock.lock()));
        
    /*TRIGGER MAPS***************************************************************** */
 




    /*POV(d-pad)MAPS************************************************************************************************************************/
      
       joystick.pov(90).whileTrue(Camera.Cam1());
     
       
       
       
       
    /*DRIVE STUFF ***********************************************************************************************************************/
       drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY()/3 * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX()/3 * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );
        
        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        
        joystick.pov(0).whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(0.5).withVelocityY(0))
        );
        joystick.pov(180).whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(-0.5).withVelocityY(0))
        );
        //joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        //joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        //joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        //joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));
        //joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));
        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        /* Run the path selected from the auto chooser */
        return autoChooser.getSelected();
    }
}
