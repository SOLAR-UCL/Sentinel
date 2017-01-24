/*
 * Copyright 2016 Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.ufpr.inf.gres;

import java.io.Serializable;

/**
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 * @version 1.0
 */
public class TriTyp implements Serializable {

	public static final int EQUILATERO = 3;
	public static final int ESCALENO = 1;
	public static final int ISOSCELES = 2;
	public static final int NO_TRIANGULO = 0;

	private static final long serialVersionUID = 1L;
	public int i, j, k;
	public int tipo;

	public TriTyp() {
	}

	public void setI(int v) throws IllegalArgumentException {
		if (v <= 0) {
			throw new IllegalArgumentException();
		}
		i = v;
	}

	public void setJ(int v) throws IllegalArgumentException {
		if (v <= 0) {
			throw new IllegalArgumentException();
		}
		j = v;
	}

	public void setK(int v) throws IllegalArgumentException {
		if (v <= 0) {
			throw new IllegalArgumentException();
		}
		k = v;
	}

	/**
	 * @return 1 if scalene; 2 if isosceles; 3 if equilateral; 0 if not a triangle
	 */
	public int getTipo() {
		if (i == j) {
			tipo = tipo + 1;
		}
		if (i == k) {
			tipo = tipo + 2;
		}
		if (j == k) {
			tipo = tipo + 3;
		}

		if (i <= 0 || j <= 0 || k <= 0) {
			tipo = TriTyp.NO_TRIANGULO;
			return tipo;
		}

		if (tipo == 0) {
			if (i + j <= k || j + k <= i || i + k <= j) {
				tipo = TriTyp.NO_TRIANGULO;
				return tipo;
			} else {
				tipo = TriTyp.ESCALENO;
				return tipo;
			}
		}
		if (tipo > 3) {
			tipo = TriTyp.EQUILATERO;
			return tipo;
		} else {
			if (tipo == 1 && i + j > k) {
				tipo = TriTyp.ISOSCELES;
				return tipo;
			} else {
				if (tipo == 2 && i + k > j) {
					tipo = TriTyp.ISOSCELES;
					return tipo;
				} else {
					if (tipo == 3 && j + k > i) {
						tipo = TriTyp.ISOSCELES;
						return tipo;
					} else {
						tipo = TriTyp.NO_TRIANGULO;
						return tipo;
					}
				}
			}
		}
	}

}
