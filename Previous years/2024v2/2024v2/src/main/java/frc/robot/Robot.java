
package frc.robot;

//imports data needed to write code 
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Robot extends TimedRobot {
  // sets up physical equipment
  private XboxController joy1 = new XboxController(0);
  static final CANSparkMax FL_M = new CANSparkMax(1, MotorType.kBrushless);
  static final CANSparkMax FR_M = new CANSparkMax(2, MotorType.kBrushless);
  static final CANSparkMax RR_M = new CANSparkMax(3, MotorType.kBrushless);
  static final CANSparkMax RL_M = new CANSparkMax(4, MotorType.kBrushless);
  Spark L_1 = new Spark(2);
  Spark L_2 = new Spark(0);
  Spark Climb = new Spark(1);
  Spark L_3 = new Spark(3);
  Compressor C_0 = new Compressor(0, PneumaticsModuleType.CTREPCM);

  DoubleSolenoid ARM_L = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 6, 7);

  public MecanumDrive m_RobotDrive = new MecanumDrive(FL_M, RL_M, FR_M, RR_M);

  // sets up variables and encoders
  // private RelativeEncoder FL_E;
  // private RelativeEncoder FR_E;
  // private RelativeEncoder RR_E;
  // private RelativeEncoder RL_E;
  double speed = 0;
  double turn = 0;
  double strafe = 0;

  // sets up auton chooser(not the auton it self)
  private SendableChooser<String> Auto = new SendableChooser<>();
  private Timer time = new Timer();
  // private Timer shoot = new Timer();
  private static final String Base = "Base";
  private static final String None = "None";
  private static final String Shoot = "shoot";
  private static final String Shoot_no_move = "Shoot_no_move";

  // region Climber
  // makes d-pad read in true or false variable
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
  // endregion Climber

  // runs once when robots is enabled
  @Override
  public void robotInit() {
    // puts camare on smartdash
    CameraServer.startAutomaticCapture(0);

    // starts compressor
    C_0.enableDigital();

    // inverts motor output
    FR_M.setInverted(true);
    RR_M.setInverted(true);
    // puts chooser options on smartdash
    Auto.addOption("None", None);
    Auto.addOption("Base", Base);
    Auto.addOption("shoot", Shoot);
    Auto.addOption("Shoot_no_move", Shoot_no_move);
    SmartDashboard.putData("Auton Mode", Auto);

  }

  // runs while robot is enabled
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("lspeed", L_1.get());

    // // make encoder a readable number
    // FL_E = FL_M.getEncoder();
    // FR_E = FR_M.getEncoder();
    // RR_E = RR_M.getEncoder();
    // RL_E = RL_M.getEncoder();

    // puts encoder on smartdash
    // SmartDashboard.putNumber("FL_E postion", FL_E.getPosition());
    // SmartDashboard.putNumber("FL_E velocity", FL_E.getVelocity());
    // SmartDashboard.putNumber("FR_E postion", FR_E.getPosition());
    // SmartDashboard.putNumber("FR_E velocity", FR_E.getVelocity());
    // SmartDashboard.putNumber("RR_E postion", RR_E.getPosition());
    // SmartDashboard.putNumber("RR_E velocity", RR_E.getVelocity());
    // SmartDashboard.putNumber("RL_E postion", RL_E.getPosition());
    // SmartDashboard.putNumber("RL_E velocity", RL_E.getVelocity());

    SmartDashboard.putNumber("speed", speed);
    SmartDashboard.putNumber("strafe", strafe);
    SmartDashboard.putNumber("turn", turn);

  }

  // runs once at start of auton
  @Override
  public void autonomousInit() {
    time.reset();
    time.start();
  }

  // runs through out auton
  @Override
  public void autonomousPeriodic() {
    // moves forward
    if (Auto.getSelected() == Base) {
      if (time.get() < 4) {
        RL_M.set(-0.15);
        RR_M.set(-0.15);
        FR_M.set(-.15);
        FL_M.set(-.15);
      } else if (time.get() > 4) {
        RL_M.set(0);
        RR_M.set(0);
        FR_M.set(0);
        FL_M.set(0);
      } else {
        RL_M.set(0);
        RR_M.set(0);
        FR_M.set(0);
        FL_M.set(0);
      }
    }
    if (Auto.getSelected() == Shoot) {
      if (time.get() < 3) {
        L_1.set(1);
        L_2.set(1);
        L_3.set(-1);
      } else if (time.get() > 3 && time.get() < 7) {
        L_1.set(0);
        L_2.set(0);
        L_3.set(0);
        RL_M.set(-0.10);
        RR_M.set(-0.10);
        FR_M.set(-.10);
        FL_M.set(-.10);
      } else if (time.get() > 7) {
        RL_M.set(0);
        RR_M.set(0);
        FR_M.set(0);
        FL_M.set(0);
      }
    }
    if (Auto.getSelected() == Shoot_no_move) {
      if (time.get() < 3) 
        L_1.set(1);
        L_2.set(1);
        L_3.set(-1);
      } else if (time.get() > 3 && time.get() < 7) {
        L_1.set(0);
        L_2.set(0);
        L_3.set(0);

      }
    }

  }

  // runs once at start of teleop
  @Override
  public void teleopInit() {
  }

  // runs through out teleop
  @Override
  public void teleopPeriodic() {
    // set var to joystick imput

    //region deadband
    //right joystick moving up or down
    if (joy1.getRawAxis(5) >= .1) {
    speed = -(joy1.getRawAxis(5));
    } else if (joy1.getRawAxis(5) <= -.1) {
    speed = -(joy1.getRawAxis(5));
    } else {
    speed = 0;
    }

    //right x joystick moving left OR right
    if (joy1.getRawAxis(4) >= .1) {
    turn = joy1.getRawAxis(4);
    } else if (joy1.getRawAxis(4) <= -.1) {
    turn = joy1.getRawAxis(4);
    } else {
    turn = 0;
    }


    //left x joystick moving left OR right
    if (joy1.getRawAxis(0) >= .1) {
    strafe = joy1.getRawAxis(0);
    } else if (joy1.getRawAxis(0) <= -.1) {
    strafe = joy1.getRawAxis(0);
    } else {
    strafe = 0;
    }
    //endregion deadband
    
// drives robots
    // FL_M.set(speed + turn + strafe);
    // FR_M.set(speed - turn - strafe);
    // RL_M.set(speed + turn - strafe);
    // RR_M.set(speed - turn + strafe);
    m_RobotDrive.driveCartesian(speed, strafe, turn);

    // sets shooter speed
  if (joy1.getRawButton(6)) {
      L_1.set(-1);
     
    } else {
      L_1.set(0);
    
    }

    // climber
    if (joy1.getRawAxis(2) == 1) {
      Climb.set(1);
    } else if (joy1.getRawButton(5)) {
      Climb.set(-1);
    } else {
      Climb.set(0);
    }

    // climber lock
    
    if (DPadRight() == true) {
      ARM_L.set(Value.kReverse);
    } else if (DPadLeft() == true) {
      ARM_L.set(Value.kForward);
    }

  }

  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}
