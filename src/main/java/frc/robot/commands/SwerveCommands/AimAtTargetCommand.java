// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.SwerveCommands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.Swerve.SwerveSubsystem;


public class AimAtTargetCommand extends Command {

  PIDController  pidController = new PIDController(0.8, 0, 0);
  CameraSubsystem cameraSubsystem;
  SwerveSubsystem swerveSubsystem;
  PIDController xmovingController = new PIDController(2.8, 0, 0);
  PIDController ymovingController = new PIDController(0.8, 0, 0);
  
  /** Creates a new AimAtTargetCommand. */
  public AimAtTargetCommand(CameraSubsystem camsubsystem ,  SwerveSubsystem swerveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.cameraSubsystem = camsubsystem;
    this.swerveSubsystem = swerveSubsystem;
    addRequirements(cameraSubsystem , swerveSubsystem);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      //double movingx = MathUtil.clamp(xmovingController.calculate(cameraSubsystem.getYOffset(), 15.0) / 20.0, -1.0, 1.0);
      //double movingy = MathUtil.clamp(ymovingController.calculate(-cameraSubsystem.getXOffset()) / 20.0, -1.0, 1.0);
      double movingx = MathUtil.clamp(xmovingController.calculate((swerveSubsystem.getEstimatedApriltagPose().getX()), -1.0), -1, 1.0);
      double movingy = MathUtil.clamp(ymovingController.calculate((swerveSubsystem.getEstimatedApriltagPose().getY()), 0), -1, 1.0);
      //double steeringAdjust = -MathUtil.clamp(pidController.calculate((swerveSubsystem.getEstimatedPose().getRotation().getDegrees()), 0) / 20.0, -1.0, 1.0);
      //SmartDashboard.putNumber("ye2", movingy);
      //SmartDashboard.putNumber("iks2", movingx);
      //SmartDashboard.putNumber("angleis", swerveSubsystem.getEstimatedPose().getRotation().getDegrees());
        //double steeringAdjust = MathUtil.clamp(pidController.calculate(-cameraSubsystem.getXOffset()) / 20.0, -1.0, 1.0);
      //SmartDashboard.putNumber("steer", steeringAdjust);
      //ChassisSpeeds chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(movingx * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond / 2, 0.0,steeringAdjust * DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond / 4, swerveSubsystem.getRotation2d());
      ChassisSpeeds chassisSpeeds = new ChassisSpeeds(movingx * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond / 2,movingy * DriveConstants.kTeleDriveMaxSpeedMetersPerSecond / 2 , 0.0);
    // steering value :: steeringAdjust * DriveConstants.kTeleDriveMaxAngularSpeedRadiansPerSecond / 4
      SwerveModuleState[] swerveModuleState = DriveConstants.kDriveKinematics.toSwerveModuleStates(chassisSpeeds);
      swerveSubsystem.setModuleStates(swerveModuleState);

  } //önemli

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerveSubsystem.stopModules();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
