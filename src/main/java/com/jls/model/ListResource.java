package com.jls.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jls on 09/03/17.
 */
@Data
@NoArgsConstructor
public class ListResource<T>  {

    List<T> items = new ArrayList<>();
}
