package com.fisa.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fisa.exception.DeptNotFoundException;
import com.fisa.model.dao.DeptCopyRepository;
import com.fisa.model.domain.entity.DeptCopy;

@RestController
public class DeptCopyController {

	@Autowired // 타입에 맞는 spring bean 등록
	private DeptCopyRepository dao;
	
	@PostMapping("insert")
	public DeptCopy insertDept(DeptCopy datas) {
		System.out.println("****" + datas);
		return dao.save(datas);
	}

	@GetMapping("deptone")
	public DeptCopy getDept(int deptno) throws Exception {
		Optional<DeptCopy> dept = dao.findById(deptno);
		dept.orElseThrow(Exception::new);
		return dept.get();
	}

	@GetMapping("deptall")
	public Iterable<DeptCopy> getDeptAll() {
		return dao.findAll();
	}

	@GetMapping("deletedept")
	public String deleteDept(int deptno) throws DeptNotFoundException {
		dao.findById(deptno).orElseThrow(DeptNotFoundException::new);
		dao.deleteById(deptno);
		return "삭제 완료";
	}

	@ExceptionHandler(DeptNotFoundException.class)
	public String exHandler(DeptNotFoundException e) {
		e.printStackTrace();
		return "해당 부서는 존재하지 않습니다.";
	}

	@ExceptionHandler
	public String exHandler(Exception e) {
		e.printStackTrace();
		return "요청시 입력 데이터 재 확인 부탁합니다";
	}

}
