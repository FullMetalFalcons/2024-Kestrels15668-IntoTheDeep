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
public class ParkAuto extends LinearOpMode {
    public void runOpMode() {
        //KestrelArm kestrelArm = new KestrelArm(hardwareMap, telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-10.9, 63, Math.toRadians(-90)));

        //KestrelArm arm = new KestrelArm(hardwareMap, telemetry);

        Action auto;

        Action wait;

        wait = drive.actionBuilder((drive.pose))
                .waitSeconds(3)
                .build();


        auto = drive.actionBuilder(drive.pose)
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(-58.5, 60), Math.toRadians(-90))  //Move towards basket



                .build();


        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        auto
                )
        );
    }
    public void putInBasket() {
        System.out.println("PLACEHOLDER");
    }
}