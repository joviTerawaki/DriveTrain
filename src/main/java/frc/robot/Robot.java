// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import com.kauailabs.navx.frc.AHRS;
// testing on old robot 
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX; 
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private CANSparkMax leftFront; // motor controllers
  //private CANSparkMax leftMid; 
  //private CANSparkMax leftBack;
  private WPI_TalonSRX leftBack; // test on old robot 
  private WPI_VictorSPX rightBack; // test on old robot 
  private CANSparkMax rightFront; 
  //private CANSparkMax rightMid; 
  //private CANSparkMax rightBack; 
  private Joystick joystick2; 
  private Joystick joystick; 
  private DoubleSolenoid shifter;
  public Drive drive; // drive class object
  public Shifter shifterClass; // shifter class object
  private AHRS navX; 

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftFront = new CANSparkMax(11, MotorType.kBrushless); // CHANGE PORT ID 
    //leftMid = new CANSparkMax(12, MotorType.kBrushless); 
    //leftBack = new CANSparkMax(3, MotorType.kBrushless); 
    leftBack = new WPI_TalonSRX(8); // motors needed to test on old robot 
    rightBack = new WPI_VictorSPX(5); // motors needed to test on old robot 
    rightFront = new CANSparkMax(7, MotorType.kBrushless); 
    //rightMid = new CANSparkMax(8, MotorType.kBrushless); 
    //rightBack = new CANSparkMax(6, MotorType.kBrushless); 
    joystick2 = new Joystick(1); 
    joystick = new Joystick(0); 
    shifter = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 6);

    drive = new Drive(leftFront, /*leftMid,*/ leftBack, rightFront, /*rightMid,*/ rightBack); 
    shifterClass = new Shifter(shifter); 
    navX = new AHRS(); 
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("navX pitch", navX.getPitch());

    //drive.arcadeDrive(joystick.getX(), -joystick.getY());      // arcade drive
    drive.tankDrive(joystick.getY(), joystick2.getY());          // tank drive

    // Button 5(set speed) Button 6(set power) 
    if(joystick.getRawButton(5)) { 
      shifterClass.setSpeed(); 
    }
    else if(joystick.getRawButton(6)) {
      shifterClass.setPower(); 
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
