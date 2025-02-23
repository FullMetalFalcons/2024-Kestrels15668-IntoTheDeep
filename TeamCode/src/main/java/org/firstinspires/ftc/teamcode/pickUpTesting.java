package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous
public class pickUpTesting extends LinearOpMode {
    public void runOpMode() {
        // Initialize subsystems
        KestrelArm kestrelArm = new KestrelArm(hardwareMap, telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(34.6, 63.3, Math.toRadians(-90)));

        // Create a wait action that waits for 3 seconds
        Action waitAction = new KestrelArm.WaitAction(3);

        Action armMove = new KestrelArm.ArmRotatorToPosition(kestrelArm, 50, 2.0);

        waitForStart();
        if (isStopRequested()) return;
        runOpMode();
    }



}
