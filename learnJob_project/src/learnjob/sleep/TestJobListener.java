package learnjob.sleep;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;

import job.LightOffJobWithNonRull;
import job.LightOnJobWithNonRull;
import learnJob.JobManagerUtil;

public class TestJobListener {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, OperationCanceledException, InterruptedException {
//		((JobManager)(Job.getJobManager())).optionsChanged(new DebugOptionImp());
		testDifferentInstance1();
	}

	private static void testDifferentInstance() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, OperationCanceledException, InterruptedException {
		LightOnJobWithNonRull lightOn1 = new LightOnJobWithNonRull();
		
		lightOn1.addJobChangeListener(new IJobChangeListener() {
			
			@Override
			public void sleeping(IJobChangeEvent event) {
				JobManagerUtil.print("call the sleeping method after receive the event");
				printAllJobs();
			}

			/**
			 *  [main] : SLEEPING == >
				[main] : WAITING == >
				[main] : RUNNING == >
			 */
			@Override
			public void scheduled(IJobChangeEvent event) {
				JobManagerUtil.print("call the scheduled method after receive the event");
				printAllJobs();
			}
			
			/**
			 *  [Worker-0] : call the running method after receive the event
				[Worker-0] : SLEEPING == >
				[Worker-0] : WAITING == >
				[Worker-0] : RUNNING == >
			 */
			@Override
			public void running(IJobChangeEvent event) {
				JobManagerUtil.print("call the running method after receive the event");
				printAllJobs();
			}
			
			@Override
			public void done(IJobChangeEvent event) {
				JobManagerUtil.print("call the done method after receive the event");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Job lightoffJob = new LightOffJobWithNonRull();
				lightoffJob.schedule();
				
				printAllJobs();
			}
			
			@Override
			public void awake(IJobChangeEvent event) {
				JobManagerUtil.print("call the awake method after receive the event");
				printAllJobs();
			}
			
			/**
			 *  [Worker-0] : call the aboutToRun method after receive the event
				[Worker-0] : SLEEPING == >
				[Worker-0] : WAITING == >
				[Worker-0] : RUNNING == >
			 */
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				JobManagerUtil.print("call the aboutToRun method after receive the event");
				printAllJobs();
			}
			
			private void printAllJobs() {
				try {
					JobManagerUtil.printAllJobs();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		lightOn1.schedule();

		JobManagerUtil.waitForJobs(3000);
		
		Thread.sleep(5000);
		JobManagerUtil.printAllJobs();
	}
	
	private static void testDifferentInstance1() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, OperationCanceledException, InterruptedException {
		LightOnJobWithNonRull lightOn1 = new LightOnJobWithNonRull();
		
		lightOn1.addJobChangeListener(new IJobChangeListener() {
			
			@Override
			public void sleeping(IJobChangeEvent event) {
				JobManagerUtil.print("call the sleeping method after receive the event");
				printAllJobs();
			}
			
			/**
			 *  [main] : SLEEPING == >
				[main] : WAITING == >
				[main] : RUNNING == >
			 */
			@Override
			public void scheduled(IJobChangeEvent event) {
				JobManagerUtil.print("call the scheduled method after receive the event");
				printAllJobs();
			}
			
			/**
			 *  [Worker-0] : call the running method after receive the event
				[Worker-0] : SLEEPING == >
				[Worker-0] : WAITING == >
				[Worker-0] : RUNNING == >
			 */
			@Override
			public void running(IJobChangeEvent event) {
				JobManagerUtil.print("call the running method after receive the event");
				printAllJobs();
			}
			
			@Override
			public void done(IJobChangeEvent event) {
				printAllJobs();https://github.com/zhanglin2018/learnJob.git

				JobManagerUtil.print("call the done method after receive the event");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				printAllJobs();
			}
			
			@Override
			public void awake(IJobChangeEvent event) {
				JobManagerUtil.print("call the awake method after receive the event");
				printAllJobs();
			}
			
			/**
			 *  [Worker-0] : call the aboutToRun method after receive the event
				[Worker-0] : SLEEPING == >
				[Worker-0] : WAITING == >
				[Worker-0] : RUNNING == >
			 */
			@Override
			public void aboutToRun(IJobChangeEvent event) {
				JobManagerUtil.print("call the aboutToRun method after receive the event");
				printAllJobs();
			}
			
			private void printAllJobs() {
				try {
					JobManagerUtil.printAllJobs();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		lightOn1.schedule();
		
		Thread.sleep(1000);  // result
		lightOn1.schedule();
		
		JobManagerUtil.waitForJobs(2000);
		
		Thread.sleep(5000);
		JobManagerUtil.printAllJobs();
	}
}
