package learnJob;

import java.io.File;
import java.util.Map;

import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugTrace;

public class DebugOptionImp implements DebugOptions {

	@Override
	public boolean getBooleanOption(String option, boolean defaultValue) {
		return true;
	}

	@Override
	public String getOption(String option) {
		return null;
	}

	@Override
	public String getOption(String option, String defaultValue) {
		return null;
	}

	@Override
	public int getIntegerOption(String option, int defaultValue) {
		return 0;
	}

	@Override
	public Map<String, String> getOptions() {
		return null;
	}

	@Override
	public void setOption(String option, String value) {

	}

	@Override
	public void setOptions(Map<String, String> options) {

	}

	@Override
	public void removeOption(String option) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public void setDebugEnabled(boolean value) {

	}

	@Override
	public void setFile(File newFile) {

	}

	@Override
	public File getFile() {
		return null;
	}

	@Override
	public DebugTrace newDebugTrace(String bundleSymbolicName) {
		return new DebugTrace() {
			
			@Override
			public void traceExit(String option, Object result) {
				System.out.println(option + " ===> " + result.toString());
			}
			
			@Override
			public void traceExit(String option) {
				System.out.println(option + " ===> ");
			}
			
			@Override
			public void traceEntry(String option, Object[] methodArguments) {
				System.out.println(option + " ===> " + methodArguments.toString());

			}
			
			@Override
			public void traceEntry(String option, Object methodArgument) {
				System.out.println(option + " ===> " + methodArgument.toString());
			}
			
			@Override
			public void traceEntry(String option) {
				System.out.println(option + " ===> ");
			}
			
			@Override
			public void traceDumpStack(String option) {
				System.out.println(option + " ===> ");
			}
			
			@Override
			public void trace(String option, String message, Throwable error) {
				System.out.println(option + " ===> " + message + " ===> " + error.toString());
			}
			
			@Override
			public void trace(String option, String message) {
				System.out.println(option + " ===> " + message);

			}
		};
	}

	@Override
	public DebugTrace newDebugTrace(String bundleSymbolicName, Class<?> traceEntryClass) {
		return null;
	}

}
