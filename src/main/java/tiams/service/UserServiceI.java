package tiams.service;

import java.util.List;

import tiams.dto.AuthDto;
import tiams.dto.DataGrid;
import tiams.dto.UserDto;
import tiams.model.User;
import tiams.util.MyException;

public interface UserServiceI {

    public UserDto login(UserDto userDto);

    public DataGrid datagrid(UserDto userDto);

    public UserDto add(UserDto userDto) throws MyException;

    public UserDto modify(UserDto userDto) throws MyException;

    public void delete(UserDto userDto);

    public List<AuthDto> getAuths(String id);

    public List<AuthDto> getAuths(UserDto u);
}
