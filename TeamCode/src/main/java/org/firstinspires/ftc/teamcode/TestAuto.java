package org.firstinspires.ftc.teamcode;

// RoadRunner Specific Imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Regular FTC Imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Config
@Autonomous
public class TestAuto extends LinearOpMode {

    int STARTING_X = -52;
    int STARTING_Y = 9;

    MecanumDrive drive = null;

    public void runOpMode() {

        drive = new MecanumDrive(hardwareMap, new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(90)));

        Action testTrajectory;

        testTrajectory = drive.actionBuilder(drive.pose)
                .strafeTo(new Vector2d(STARTING_X, STARTING_Y+12))
                .strafeTo(new Vector2d(STARTING_X-12, STARTING_Y))
                .build();


        waitForStart();
        if (isStopRequested()) return;


        Actions.runBlocking(
                new SequentialAction(
                        // Run trajectory from above
                        testTrajectory
                )
        );
    }
}