package com.yedam.app.planner.domain;


import lombok.Data;

@Data
public class BudgetVO {
	String budgetId;
	String budgetList;
	int budgetPrice;
	String budgetContent;
	String planId;
	String budgetMemo;
	String budgetDate;
	
	//일별 합계
	int sum;
}
