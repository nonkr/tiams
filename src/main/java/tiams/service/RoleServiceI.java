package tiams.service;

import tiams.dto.DataGrid;
import tiams.dto.RoleDto;
import tiams.util.MyException;

public interface RoleServiceI {

    public DataGrid datagrid(RoleDto roleDto);

    public RoleDto add(RoleDto roleDto) throws MyException;

    public RoleDto modify(RoleDto roleDto) throws MyException;

    public void delete(RoleDto roleDto);

}
