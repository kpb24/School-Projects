//Kerilee Bookleiner
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class TestHoodPopper {
	static WebDriver driver = new HtmlUnitDriver();
	
	@Before
	public void setUp() throws Exception{
		driver.get("http://lit-bayou-7912.herokuapp.com/");
	}



	//Test that a program without functions or variables doesn't show up as ":on_ident"
	//identifiers. When no input is provided and the tokenize button is clicked,
	//"on_ident" identifier(s) should not be present.
	@Test
	public void testNoIdentifier(){
		driver.findElement(By.xpath("//input[@value='Tokenize']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertFalse(s.contains(":on_ident"));
	}
	
	
	//Test that variables show up as ":on_ident" identifiers.
	//When a variable is used in a program and the tokenize button is clicked,
	//"on_ident" identifier(s) should be present.
	@Test
	public void testTokenizeVar(){
		driver.findElement(By.id("code_code")).sendKeys("x = 90");
		driver.findElement(By.xpath("//input[@value='Tokenize']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains(":on_ident"));
	}


	//Test that new lines show up as ":on_nl" identifiers.
	//When new line characters ("\n") are used in a program and the tokenize button
	//is clicked, ":on_nl" identifier(s) should be present.
	@Test
	public void testTokenizeNL(){
		driver.findElement(By.id("code_code")).sendKeys("x = 90\n");		
		driver.findElement(By.xpath("//input[@value='Tokenize']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains(":on_nl"));
	}

	
	//Test that operators show up as ":on_op" identifiers.
	//When "+" and "/" operators are used in a program and the tokenize button
	//is clicked, ":on_op" identifier(s) should be present.
	@Test
	public void testTokenizeOp(){
		driver.findElement(By.id("code_code")).sendKeys("x = 38 + 2 / 9"); 
		driver.findElement(By.xpath("//input[@value='Tokenize']")).click();		
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains(":on_op"));
	}
	
	
	//Test that spaces show up as ":on_sp" identifiers.
	//When spaces (" ") are used in a program and the tokenize button is clicked,
	//":on_sp" identifier(s) should be present.
	@Test
	public void testTokenizeSp() {
		driver.findElement(By.id("code_code")).sendKeys("x = 38 + 2");
		driver.findElement(By.xpath("//input[@value='Tokenize']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains(":on_sp"));
	}
	



	//Test that any non-white space tokens show up in the AST.
	//When "-" is used in a program and the parse button is clicked,
	//it should be present in the AST.
	@Test
	public void testParseOp(){
		driver.findElement(By.id("code_code")).sendKeys("x = 90 - 9"); 
		driver.findElement(By.xpath("//input[@value='Parse']")).click();
		String s = driver.findElement(By.xpath("/html/body/p[2]")).getText();
		assertTrue(s.contains("-"));
	}


	//Test that white space tokens do not show up in the AST.
	//When a program contains white space and the parse button is clicked,
	//white space (" ") should not be present in the AST.
	@Test
	public void testParseWhitespace(){
		driver.findElement(By.id("code_code")).sendKeys("Hello World"); 
		driver.findElement(By.xpath("//input[@value='Parse']")).click();
		String s = driver.findElement(By.xpath("/html/body/p[2]")).getText();
		assertFalse(s.contains(" "));
	}
	

	//Test that any non-white space tokens show up in the AST.
	//When a program contains the "puts" function and the parse button is clicked,
	//it should be present in the AST.
	@Test
	public void testParsePuts(){
		driver.findElement(By.id("code_code")).sendKeys("puts x = 5"); 
		driver.findElement(By.xpath("//input[@value='Parse']")).click();
		String s = driver.findElement(By.xpath("/html/body/p[2]")).getText();
		assertTrue(s.contains("puts"));
	}





	//Test that any program that contains "puts" should also have putstring operation.
	//When a program contains the "puts" function and the compile button is clicked,
	//the putstring operation should be present.
	@Test
	public void testCompilePutString(){
		driver.findElement(By.id("code_code")).sendKeys("puts \"a\""); 
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("putstring"));
	}


	//Test that any program that contains "+" calls the opt_plus operation.
	//When a program contains the "+" operation and the compile button is clicked, 
	//the opt_plus operation should be present.
	@Test
	public void testCompileAddition(){
		driver.findElement(By.id("code_code")).sendKeys("x = 90 + 9"); 
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("opt_plus"));
	}
	

	//Test that any program that contains "+" should put any values specified on the stack 
	//using the putobject operation. When a program contains the "+" operation and the 
	//compile button is clicked, the putobject operation should be present.
	@Test
	public void testCompilePutObjectPlus(){
		driver.findElement(By.id("code_code")).sendKeys("x = 34 + 12"); 
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("putobject 34"));
		assertTrue(s.contains("putobject 12"));
	}


	//Test that any program that contains "-" calls the opt_minus operation.
	//When a program contains the "-" operation and the compile button is clicked, 
	//the opt_minus operation should be present.
	@Test
	public void testCompileSubtraction(){
		driver.findElement(By.id("code_code")).sendKeys("x = 90 - 9"); 
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("opt_minus"));
	}
	
	
	//Test that any program that contains "-" should put any values specified on the stack 
	//using the putobject operation. When a program contains the "-" operation and the 
	//compile button is clicked, the putobject operation should be present.
	@Test
	public void testCompilePutObjectMinus(){
		driver.findElement(By.id("code_code")).sendKeys("x = 34 - 12"); 
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("putobject 34"));
		assertTrue(s.contains("putobject 12"));
	}


	//Test that any program that contains "/" calls the opt_div operation.
	//When a program contains the "/" operation and the compile button is clicked, 
	//the opt_div operation should be present.
	@Test
	public void testCompileDivision(){
		driver.findElement(By.id("code_code")).sendKeys("x = 90 / 9");
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("opt_div"));
	}


	//Test that any program that contains "/" should put any values specified on the stack 
	//using the putobject operation. When a program contains the "/" operation and the 
	//compile button is clicked, the putobject operation should be present.
	@Test
	public void testCompilePutObjectDiv(){
		driver.findElement(By.id("code_code")).sendKeys("x = 34 / 12"); 
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("putobject 34"));
		assertTrue(s.contains("putobject 12"));
	}


	//Test that any program that contains "*" calls the opt_mult operation.
	//When a program contains the "*" operation and the compile button is clicked, 
	//the opt_mult operation should be present.
	@Test
	public void testCompileMultiplication(){
		driver.findElement(By.id("code_code")).sendKeys("x = 90 * 9");
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("opt_mult"));
	}


	//Test that any program that contains "*" should put any values specified on the stack 
	//using the putobject operation. When a program contains the "*" operation and the 
	//compile button is clicked, the putobject operation should be present.
	@Test
	public void testCompilePutObjectMult(){
		driver.findElement(By.id("code_code")).sendKeys("x = 34 * 12"); 
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertTrue(s.contains("putobject 34"));
		assertTrue(s.contains("putobject 12"));
	}


	//Test that any program that does not contain "puts" or operators doesn't call
	//opt_plus, opt_mult, opt_div, opt_minus when the compile button is clicked.
	@Test
	public void testCompileNoOperation(){
		driver.findElement(By.id("code_code")).sendKeys("x = 90");
		driver.findElement(By.xpath("//input[@value='Compile']")).click();
		String s = driver.findElement(By.tagName("body")).getText();
		assertFalse(s.contains("opt_mult"));
		assertFalse(s.contains("opt_div"));
		assertFalse(s.contains("opt_plus"));
		assertFalse(s.contains("opt_minus"));
	}	
}