package frc.robot.subsystems.Slide;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Slide_Ladder extends SubsystemBase {
    private final Spark motor;

    public Slide_Ladder() {
        motor = new Spark(9); // Initialize the Spark motor controller on PWM port 0
    }

    public Command Down() {
        return runOnce(
            () -> {
                motor.set(-.85); // Set motor speed to -50% for downward movement
            }
        );
    }

    public Command Up() {
        return runOnce(
            () -> {
                motor.set(1); // Set motor speed to 50% for upward movement
            }
        );
    }

    public Command Stop() {
        return runOnce(
            () -> {
                motor.set(0); // Stop the motor
            }
        );
    }

}
