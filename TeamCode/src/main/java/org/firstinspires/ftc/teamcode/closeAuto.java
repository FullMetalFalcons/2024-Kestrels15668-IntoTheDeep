package org.firstinspires.ftc.teamcode;

// RoadRunner Specific Imports

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/*
  Wireless Code Download: Terminal --> "adb connect 192.168.43.1:5555"
 */

@Config
@Autonomous
public class closeAuto extends LinearOpMode {
    public void runOpMode() {
        //KestrelArm kestrelArm = new KestrelArm(hardwareMap, telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(34.6, 63.3, Math.toRadians(-90)));

        //KestrelArm arm = new KestrelArm(hardwareMap, telemetry);

        Action auto;

        Action wait;

        wait = drive.actionBuilder((drive.pose))
                .waitSeconds(3)
                .build();


        auto = drive.actionBuilder(drive.pose)
                .waitSeconds(8)
                .strafeToLinearHeading(new Vector2d(63, 56), Math.toRadians(-90))  //Move towards basket
                .waitSeconds(0.6)
                .turn(Math.toRadians(130)) // Turn to basket
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(63, 49), Math.toRadians(0)) // Move backwards

                // ADD ARM CODE HERE

                .waitSeconds(0.5)
                .turn(Math.toRadians(40)) // Turn back to be perpendicular to start position
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(63, 42), Math.toRadians(0)) // Go backwards
                .waitSeconds(0.5)




                        .build();


        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        new ArmRotatorToPosition(600, 0),
                        auto
                )
        );
    }
    public void putInBasket() {
        System.out.println("PLACEHOLDER");
    }
}