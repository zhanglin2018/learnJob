package learnJob;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;


public class JobManagerUtil {
	public static List<Job> collectSleepingJob() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return collectJob(Job.SLEEPING);
	}

	public static List<Job> collectWaitingJob() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return collectJob(Job.WAITING);

	}

	public static List<Job> collectRunningJob() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return collectJob(Job.RUNNING);
	}

	@SuppressWarnings({ "unchecked", "boxing" })
	public static List<Job> collectJob(int mask) throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		IJobManager jobManager = Job.getJobManager();
		@SuppressWarnings("rawtypes")
		Class jobManagerClass = jobManager.getClass();
		@SuppressWarnings("unchecked")
		Method selectMethod = jobManagerClass.getDeclaredMethod("select", new Class[] { Object.class, int.class });
		selectMethod.setAccessible(true);
		return (List<Job>) selectMethod.invoke(jobManager, new Object[] { null, mask });
	}

	public static void printSleepingJob() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		printJobs(Job.SLEEPING);
	}

	public static void printWaitingJob() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		printJobs(Job.WAITING);
	}

	public static void printRunningJob() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		printJobs(Job.RUNNING);
	}

	public static void printAllJobs() {
		try {
			printSleepingJob();
			printWaitingJob();
			printRunningJob();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void printJobs(int mask) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		List<Job> jobs = collectJob(mask);
		StringBuilder message = new StringBuilder();
		if (mask == Job.SLEEPING) {
			message.append("SLEEPING");
		} else if (mask == Job.WAITING) {
			message.append("WAITING");
		} else if (mask == Job.RUNNING) {
			message.append("RUNNING");
		}

		message.append(" == >");

		for (Job job : jobs) {
			message.append("\n	" + job.toString());
		}

		print(message.toString());
	}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		collectSleepingJob();
	}

	public static void print(String msg) {
		System.out.println("[" + Thread.currentThread().getName() + "]" + " : " + msg);
	}

	public static final long DEF_TIMEOUT = (15 - 5) * 60 * 1000;
	private static final long CRASH_TIMEOUT = DEF_TIMEOUT + (2 * 60 * 1000);

    public static void waitForJobs() {
        waitForJobs((Object[]) null);
    }
    
    public static void waitForJobs(Object... jobFamilies) {
        waitForJobs(0, DEF_TIMEOUT, null, jobFamilies);
    }
	
	public static void waitForJobs(final long minumumDelay) {
		waitForJobs(minumumDelay, DEF_TIMEOUT, null, (Object[]) null);
	}

	public static void waitForJobs(final long minumumDelay, final long maximumDelay, Job excludeJob,
			Object... jobFamilies) {
		if (maximumDelay < minumumDelay) {
			throw new IllegalArgumentException(
					"Max delay is smaller as min delay: " + maximumDelay + "/" + minumumDelay);
		}
		long currentTimeMillis = System.currentTimeMillis();
		long minWaitMillis = currentTimeMillis + minumumDelay;
		long maxWaitMillis = currentTimeMillis + maximumDelay;
		long maxWaitMillisLimit = getMaxWaitEndTime();
		final long endTimeMax = Math.min(maxWaitMillis, maxWaitMillisLimit);
		final long endTimeMin = Math.min(minWaitMillis, endTimeMax);

		while (System.currentTimeMillis() < endTimeMax) {
			waitAMoment();
			if (!isRunning(jobFamilies)) {
				// if we are in the UI thread, run the event loop once more
				// to ensure any Runnables passed to asyncExec after the last
				// waitAMoment are executed
				processUIEvents();
				if (System.currentTimeMillis() <= endTimeMin) {
					continue;
				}
				return;
			}
			List<Job> jobs = getRunningJobs((Object) null);
			if (jobs.size() == 1 && jobs.get(0) == excludeJob) {
				if (Display.getCurrent() != null) {
					// if we are in the UI thread, run the event loop once more
					// to ensure any Runnables passed to asyncExec after the last
					// waitAMoment are executed
					processUIEvents();
				}
				if (System.currentTimeMillis() <= endTimeMin) {
					continue;
				}
				return;
			}
		}
	}

	private static long getMaxWaitEndTime() {
		// if the timeout already reached, increase timeout to allow "crash"
		return System.currentTimeMillis() + CRASH_TIMEOUT;
	}

	public static void waitAMoment() {
		waitAMoment(10);
	}

	public static void waitAMoment(long milliseconds) {
		synchronized (JobManagerUtil.class) {
			try {
				JobManagerUtil.class.wait(milliseconds);
				processUIEvents();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Process all queued UI events. Does nothing if called from outside the UI
	 * thread.
	 */
	public static void processUIEvents() {
		Display display = Display.getCurrent();
		if (display != null) {
			while (display.readAndDispatch()) {
				// process queued ui events
			}
		}
	}

	public static List<Job> getRunningJobs(Object jobFamily) {
		List<Job> running = new ArrayList<>();
		Job[] jobs = Job.getJobManager().find(jobFamily);
		for (Job job : jobs) {
			if (isRunningJob(job) || isImportantSleepingJob(job)) {
				running.add(job);
			}
		}
		return running;
	}

	private static boolean isRunningJob(Job job) {
		int state = job.getState();
		return (state == Job.RUNNING || state == Job.WAITING);
	}

	private static boolean isImportantSleepingJob(Job job) {
		int state = job.getState();
		if (state != Job.SLEEPING) {
			return false;
		}
		String jobClassName = job.getClass().getName();
		return jobClassName.startsWith("com.advantest.") || jobClassName.startsWith("com.verigy.")
				|| jobClassName.startsWith("org.eclipse.core.internal.events.AutoBuildJob");
	}
	
	public static boolean isRunning(Object jobFamily) {
        return !getRunningJobs(jobFamily).isEmpty();
    }
}
