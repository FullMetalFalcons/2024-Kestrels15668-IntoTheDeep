package org.firstinspires.ftc.teamcode;

// Road Runner Specific Imports
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous
public class closeAuto extends LinearOpMode {


    public void runOpMode() {
        // Initialize subsystems
        KestrelArm kestrelArm = new KestrelArm(hardwareMap, telemetry);
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(34.6, 63.3, Math.toRadians(-90)));

        // Create a wait action that waits for 3 seconds
        Action waitAction = new KestrelArm.WaitAction(3);

        // Build the first trajectory segment
        Action trajectory1 = drive.actionBuilder(drive.pose)
                .strafeToLinearHeading(new Vector2d(63, 56), Math.toRadians(-90))  // Move towards basket
                .waitSeconds(0.6)
                .turn(Math.toRadians(130)) // Turn to basket
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(63, 49), Math.toRadians(0)) // Move backwards

                // EXTEND ARM: Call the extendArm function here
                .waitSeconds(0.5)  // Optional wait to let arm fully extend before proceeding
                .build();

        // Build the arm movement action (moves arm to position 600 over 2 seconds)
        Action armMove = new KestrelArm.ArmRotatorToPosition(kestrelArm, 50, 2.0);

        // Build the second trajectory segment
        Action trajectory2 = drive.actionBuilder(drive.pose)
                .turn(Math.toRadians(40)) // Turn back to be perpendicular to start position
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(63, 42), Math.toRadians(0)) // Go backwards
                .waitSeconds(0.5)
                .build();

        // Combine all actions into a single sequential action
        Action auto = new SequentialAction(
                waitAction,    // Wait for 3 seconds before starting
                trajectory1,   // Execute the first trajectory segment
                armMove,       // Run the arm movement action
                trajectory2    // Execute the second trajectory segment
        );

        waitForStart();
        if (isStopRequested()) return;

        // Run the complete autonomous sequence
        Actions.runBlocking(auto);
    }

    public void extendArm(KestrelArm kestrelArm) {
        Action armMove = new KestrelArm.ArmRotatorToPosition(kestrelArm, 600, 2.0);
        Actions.runBlocking(armMove); // Execute the arm move action
    }

    public void closeArm(KestrelArm kestrelArm) {
        Action armMove = new KestrelArm.ArmRotatorToPosition(kestrelArm, 0, 2.0); // Moves arm to position 0
        Actions.runBlocking(armMove); // Execute the arm move action
    }
}
