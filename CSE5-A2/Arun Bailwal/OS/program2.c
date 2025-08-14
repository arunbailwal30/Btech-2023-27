"""Build Environment used for isolation during sdist building"""

from __future__ import annotations

import logging
import os
import pathlib
import site
import sys
import textwrap
from collections import OrderedDict
from collections.abc import Iterable
from types import TracebackType
from typing import TYPE_CHECKING, Protocol

from pip._vendor.packaging.version import Version

from pip import __file__ as pip_location
from pip._internal.cli.spinners import open_spinner
from pip._internal.locations import get_platlib, get_purelib, get_scheme
from pip._internal.metadata import get_default_environment, get_environment
from pip._internal.utils.log