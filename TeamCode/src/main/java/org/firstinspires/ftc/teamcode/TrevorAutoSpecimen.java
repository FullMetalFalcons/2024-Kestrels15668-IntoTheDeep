package org.firstinspires.ftc.teamcode;

// RoadRunner Specific Imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
  Wireless Code Download: Terminal --> "adb connect 192.168.43.1:5555"
 */

@Config
@Autonomous
public class TrevorAutoSpecimen extends LinearOpMode {
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, Math.toRadians(90)));
        KestrelArm arm = new KestrelArm(hardwareMap, telemetry);

        Action moveToPark;
        Action moveToChamber;


        moveToPark = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(48,1))
                .build();
        moveToChamber = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(0,24))
                .build();


        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                    arm.wristToPosition(0.229),
                    arm.armToPosition(40,0,1000),
                    arm.wristToPosition(0.317),
                    arm.armToPosition(48,8,20),

                    moveToChamber,
                    arm.armToPosition(35,7.5,20),
                    arm.armToPosition(40,7.5,20),

                    moveToPark,
                    arm.armToPosition(0,0,20),

                    new SleepAction(1),
                    arm.wristToPosition(0.229),
                    arm.armToPosition(0,0,20)
                )
        );
    }
}