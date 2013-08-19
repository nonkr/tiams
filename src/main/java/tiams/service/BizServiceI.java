package tiams.service;

import java.util.List;

import tiams.dto.BizAcceptDto;
import tiams.dto.BizQueryDto;
import tiams.dto.DataGrid;
import tiams.util.MyException;

public interface BizServiceI {
    public DataGrid datagrid(BizQueryDto bizQueryDto);

    public BizAcceptDto add(BizAcceptDto bizAcceptDto) throws MyException;
}
