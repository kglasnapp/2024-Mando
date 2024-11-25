package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.TestCmd;
import frc.robot.subsystems.Joysticks;
//import frc.robot.commands.DriveStraight;

//import frc.robot.commands.DriveStraight.DriveMode;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  static enum Action {
    PRESSED, RELEASED
  }

  private ArrayList<ButtonHandler> buttons = new ArrayList<ButtonHandler>();

  void setShooterPOV(Joystick joy, int angle, int speed) {
    // new POVButton(joy, angle).whenPressed(new
    // ShooterCmd(ShooterMode.SET_SHOOTER_VELOCITY, speed));
  }

  OI() {
    SmartDashboard.putData("TestTask", new TestCmd());
  }

  final public int defaultShot = 6;

 
  // Left Joy actions
  public boolean driveStraightPressed() {
    if ((Robot.driveJoy)) {
      return Joysticks.leftJoy.getTriggerPressed();
    }
    if (Joysticks.driver != null)
      return Joysticks.driver.getRawButtonPressed(5);
    if (Joysticks.leftJoy == null)
      return Joysticks.operator.getRawButtonPressed(5);
    return Joysticks.leftJoy.getTriggerPressed();
  }

  public boolean driveStraightReleased() {
    if (Robot.driveJoy) {
      return Joysticks.leftJoy.getTriggerReleased();
    }
    if (Joysticks.driver != null)
      return Joysticks.driver.getRawButtonReleased(5);
    if (Joysticks.leftJoy == null)
      return Joysticks.operator.getRawButtonReleased(5);
    return Joysticks.leftJoy.getTriggerReleased();
  }

  public double driveStraightSpeed() {
    if (Robot.driveJoy) {
      return (Joysticks.rightJoy.getY() + Joysticks.leftJoy.getY()) / 2;
    }
    if (Joysticks.driver != null) {
      return (Joysticks.driver.getRawAxis(5) + Joysticks.driver.getRawAxis(1)) / 2;
    }
    if (Joysticks.leftJoy == null)
      return (Joysticks.operator.getRawAxis(5) + Joysticks.operator.getRawAxis(1)) /1.0;
    return (Joysticks.rightJoy.getY() + Joysticks.leftJoy.getY()) / 2;
  }

  public double leftJoySpeed() {
    if (Robot.driveJoy) {
      return -Joysticks.leftJoy.getY();
    }
    if (Joysticks.driver != null) {
      return -Joysticks.driver.getRawAxis(1);
    }
    if (Joysticks.rightJoy == null)
      return -Joysticks.operator.getRawAxis(1);
    return -Joysticks.leftJoy.getY();
  }

  // Right Joy Actions

  public double rightJoySpeed() {
    if (Robot.driveJoy) {
      return -Joysticks.rightJoy.getY();
    }
    if (Joysticks.driver != null) {
      return -Joysticks.driver.getRawAxis(5);
    }
    if (Joysticks.rightJoy == null)
      return -Joysticks.operator.getRawAxis(5);
    return -Joysticks.rightJoy.getY();
  }

  public boolean ballTrackActive() {
    return Joysticks.rightJoy.getRawButton(2);
  }

  // Operator actions
  public boolean clearStickyAndLogCurrents() {
    return Joysticks.operator.getRawButtonPressed(9);
  }

  public boolean turboMode() {
    if (Joysticks.leftJoy == null)
      return false;
    return Joysticks.leftJoy.getPOV() == 0;
  }

  class ButtonHandler {
    int port;
    Joystick joystick;
    int buttonNumber;
    Action act;
    String name;

    private ButtonHandler(Joystick joystick, int buttonNumber, Action act, Command cmd, String name) {
      if (joystick == null)
        return;
      this.joystick = joystick;
      this.buttonNumber = buttonNumber;
      this.act = act;
      this.name = name;
      port = joystick.getPort();
      buttons.add(this);
      JoystickButton button = new JoystickButton(joystick, buttonNumber);
      if (act == Action.PRESSED)
      button.onTrue(cmd);
        //button.whenPressed(cmd);
      if (act == Action.RELEASED)
        button.onFalse(cmd);
      // todo took out button.close();
    }

    String getData() {
      return "Button:" + name + " Port:" + port + " Button:" + buttonNumber + " Action:" + act;
    }
  }
}